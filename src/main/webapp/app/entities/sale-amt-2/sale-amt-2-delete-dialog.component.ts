import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt2 } from './sale-amt-2.model';
import { SaleAmt2PopupService } from './sale-amt-2-popup.service';
import { SaleAmt2Service } from './sale-amt-2.service';

@Component({
    selector: 'jhi-sale-amt-2-delete-dialog',
    templateUrl: './sale-amt-2-delete-dialog.component.html'
})
export class SaleAmt2DeleteDialogComponent {

    saleAmt2: SaleAmt2;

    constructor(
        private saleAmt2Service: SaleAmt2Service,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleAmt2Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'saleAmt2ListModification',
                content: 'Deleted an saleAmt2'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-amt-2-delete-popup',
    template: ''
})
export class SaleAmt2DeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleAmt2PopupService: SaleAmt2PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.saleAmt2PopupService
                .open(SaleAmt2DeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
