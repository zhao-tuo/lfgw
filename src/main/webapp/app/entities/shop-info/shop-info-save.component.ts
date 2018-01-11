import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShopInfo } from './shop-info.model';
import { ShopInfoPopupService } from './shop-info-popup.service';
import { ShopInfoService } from './shop-info.service';
import { ProjectInfo, ProjectInfoService } from '../project-info';
import { ResponseWrapper } from '../../shared';
import {NgForm} from "@angular/forms";
import {Subscription} from "rxjs/Subscription";
import {isUndefined} from "util";

@Component({
    selector: 'jhi-shop-info-save',
    templateUrl: './shop-info-save.component.html'
})
export class ShopInfoSaveComponent implements OnInit {

    shopInfo: ShopInfo =new ShopInfo();
    isSaving: boolean;
    private subscription: Subscription;

    projectinfos: ProjectInfo[];


    constructor(
        private jhiAlertService: JhiAlertService,
        private shopInfoService: ShopInfoService,
        private projectInfoService: ProjectInfoService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        //todo: 判断save 还是 update
        this.subscription = this.route.params.subscribe((params) => {
            if(params.hasOwnProperty("id")){
                this.load(params['id']);
            }
        });

        this.projectInfoService.query()
            .subscribe((res: ResponseWrapper) => {
                this.projectinfos = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    load(id) {
        this.shopInfoService.find(id).subscribe((shopInfo) => {
            this.shopInfo = shopInfo;
        });
    }

    previous(){
        window.history.back();
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
        window.history.back();
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

