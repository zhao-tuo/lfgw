import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt2 } from './sale-amt-2.model';
import { SaleAmt2PopupService } from './sale-amt-2-popup.service';
import { SaleAmt2Service } from './sale-amt-2.service';

@Component({
    selector: 'jhi-sale-amt-2-dialog',
    templateUrl: './sale-amt-2-dialog.component.html'
})
export class SaleAmt2DialogComponent implements OnInit {

    saleAmt2: SaleAmt2;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private saleAmt2Service: SaleAmt2Service,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.saleAmt2.id !== undefined) {
            this.subscribeToSaveResponse(
                this.saleAmt2Service.update(this.saleAmt2));
        } else {
            this.subscribeToSaveResponse(
                this.saleAmt2Service.create(this.saleAmt2));
        }
    }

    private subscribeToSaveResponse(result: Observable<SaleAmt2>) {
        result.subscribe((res: SaleAmt2) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SaleAmt2) {
        this.eventManager.broadcast({ name: 'saleAmt2ListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sale-amt-2-popup',
    template: ''
})
export class SaleAmt2PopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleAmt2PopupService: SaleAmt2PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.saleAmt2PopupService
                    .open(SaleAmt2DialogComponent as Component, params['id']);
            } else {
                this.saleAmt2PopupService
                    .open(SaleAmt2DialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
