import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MenuData } from './menu-data.model';
import { MenuDataPopupService } from './menu-data-popup.service';
import { MenuDataService } from './menu-data.service';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-menu-data-dialog',
    templateUrl: './menu-data-dialog.component.html'
})
export class MenuDataDialogComponent implements OnInit {

    menuData: MenuData;
    isSaving: boolean;

    menudata: MenuData[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private menuDataService: MenuDataService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.menuDataService.query()
            .subscribe((res: ResponseWrapper) => { this.menudata = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.menuData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.menuDataService.update(this.menuData));
        } else {
            this.subscribeToSaveResponse(
                this.menuDataService.create(this.menuData));
        }
    }

    private subscribeToSaveResponse(result: Observable<MenuData>) {
        result.subscribe((res: MenuData) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MenuData) {
        this.eventManager.broadcast({ name: 'menuDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMenuDataById(index: number, item: MenuData) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-menu-data-popup',
    template: ''
})
export class MenuDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private menuDataPopupService: MenuDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.menuDataPopupService
                    .open(MenuDataDialogComponent as Component, params['id']);
            } else {
                this.menuDataPopupService
                    .open(MenuDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
