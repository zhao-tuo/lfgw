import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt1 } from './sale-amt-1.model';
import { SaleAmt1PopupService } from './sale-amt-1-popup.service';
import { SaleAmt1Service } from './sale-amt-1.service';

@Component({
    selector: 'jhi-sale-amt-1-delete-dialog',
    templateUrl: './sale-amt-1-delete-dialog.component.html'
})
export class SaleAmt1DeleteDialogComponent {

    saleAmt1: SaleAmt1;

    constructor(
        private saleAmt1Service: SaleAmt1Service,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleAmt1Service.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'saleAmt1ListModification',
                content: 'Deleted an saleAmt1'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-amt-1-delete-popup',
    template: ''
})
export class SaleAmt1DeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleAmt1PopupService: SaleAmt1PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.saleAmt1PopupService
                .open(SaleAmt1DeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
