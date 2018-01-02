import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShopInfo } from './shop-info.model';
import { ShopInfoPopupService } from './shop-info-popup.service';
import { ShopInfoService } from './shop-info.service';

@Component({
    selector: 'jhi-shop-info-delete-dialog',
    templateUrl: './shop-info-delete-dialog.component.html'
})
export class ShopInfoDeleteDialogComponent {

    shopInfo: ShopInfo;

    constructor(
        private shopInfoService: ShopInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shopInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shopInfoListModification',
                content: 'Deleted an shopInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shop-info-delete-popup',
    template: ''
})
export class ShopInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shopInfoPopupService: ShopInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shopInfoPopupService
                .open(ShopInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
