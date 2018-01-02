import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt1 } from './sale-amt-1.model';
import { SaleAmt1PopupService } from './sale-amt-1-popup.service';
import { SaleAmt1Service } from './sale-amt-1.service';

@Component({
    selector: 'jhi-sale-amt-1-dialog',
    templateUrl: './sale-amt-1-dialog.component.html'
})
export class SaleAmt1DialogComponent implements OnInit {

    saleAmt1: SaleAmt1;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private saleAmt1Service: SaleAmt1Service,
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
        if (this.saleAmt1.id !== undefined) {
            this.subscribeToSaveResponse(
                this.saleAmt1Service.update(this.saleAmt1));
        } else {
            this.subscribeToSaveResponse(
                this.saleAmt1Service.create(this.saleAmt1));
        }
    }

    private subscribeToSaveResponse(result: Observable<SaleAmt1>) {
        result.subscribe((res: SaleAmt1) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SaleAmt1) {
        this.eventManager.broadcast({ name: 'saleAmt1ListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sale-amt-1-popup',
    template: ''
})
export class SaleAmt1PopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private saleAmt1PopupService: SaleAmt1PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.saleAmt1PopupService
                    .open(SaleAmt1DialogComponent as Component, params['id']);
            } else {
                this.saleAmt1PopupService
                    .open(SaleAmt1DialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
