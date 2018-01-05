import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShopInfo } from './shop-info.model';
import { ShopInfoPopupService } from './shop-info-popup.service';
import { ShopInfoService } from './shop-info.service';
import { ProjectInfo, ProjectInfoService } from '../project-info';
import { ResponseWrapper } from '../../shared';
import {NgForm} from "@angular/forms";

@Component({
    selector: 'jhi-shop-info-dialog',
    templateUrl: './shop-info-dialog.component.html'
})
export class ShopInfoDialogComponent implements OnInit {

    shopInfo: ShopInfo;
    isSaving: boolean;

    projectinfos: ProjectInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private shopInfoService: ShopInfoService,
        private projectInfoService: ProjectInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectInfoService.query()
            .subscribe((res: ResponseWrapper) => { this.projectinfos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectInfoById(index: number, item: ProjectInfo) {
        return item.id;
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
