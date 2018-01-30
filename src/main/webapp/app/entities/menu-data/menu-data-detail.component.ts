import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MenuData } from './menu-data.model';
import { MenuDataService } from './menu-data.service';

@Component({
    selector: 'jhi-menu-data-detail',
    templateUrl: './menu-data-detail.component.html'
})
export class MenuDataDetailComponent implements OnInit, OnDestroy {

    menuData: MenuData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private menuDataService: MenuDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMenuData();
    }

    load(id) {
        this.menuDataService.find(id).subscribe((menuData) => {
            this.menuData = menuData;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMenuData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'menuDataListModification',
            (response) => this.load(this.menuData.id)
        );
    }
}
