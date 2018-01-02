import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShopInfo } from './shop-info.model';
import { ShopInfoService } from './shop-info.service';

@Component({
    selector: 'jhi-shop-info-detail',
    templateUrl: './shop-info-detail.component.html'
})
export class ShopInfoDetailComponent implements OnInit, OnDestroy {

    shopInfo: ShopInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shopInfoService: ShopInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShopInfos();
    }

    load(id) {
        this.shopInfoService.find(id).subscribe((shopInfo) => {
            this.shopInfo = shopInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShopInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shopInfoListModification',
            (response) => this.load(this.shopInfo.id)
        );
    }
}
