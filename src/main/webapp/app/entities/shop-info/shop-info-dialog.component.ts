import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShopInfo } from './shop-info.model';
import { ShopInfoPopupService } from './shop-info-popup.service';
import { ShopInfoService } from './shop-info.service';

@Component({
    selector: 'jhi-shop-info-dialog',
    templateUrl: './shop-info-dialog.component.html'
})
export class ShopInfoDialogComponent implements OnInit {

    shopInfo: ShopInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private shopInfoService: ShopInfoService,
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
        if (this.shopInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shopInfoService.update(this.shopInfo));
        } else {
            this.subscribeToSaveResponse(
                this.shopInfoService.create(this.shopInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShopInfo>) {
        result.subscribe((res: ShopInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ShopInfo) {
        this.eventManager.broadcast({ name: 'shopInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-shop-info-popup',
    template: ''
})
export class ShopInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shopInfoPopupService: ShopInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shopInfoPopupService
                    .open(ShopInfoDialogComponent as Component, params['id']);
            } else {
                this.shopInfoPopupService
                    .open(ShopInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
