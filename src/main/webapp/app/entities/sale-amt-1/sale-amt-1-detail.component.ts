import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt1 } from './sale-amt-1.model';
import { SaleAmt1Service } from './sale-amt-1.service';

@Component({
    selector: 'jhi-sale-amt-1-detail',
    templateUrl: './sale-amt-1-detail.component.html'
})
export class SaleAmt1DetailComponent implements OnInit, OnDestroy {

    saleAmt1: SaleAmt1;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private saleAmt1Service: SaleAmt1Service,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSaleAmt1S();
    }

    load(id) {
        this.saleAmt1Service.find(id).subscribe((saleAmt1) => {
            this.saleAmt1 = saleAmt1;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSaleAmt1S() {
        this.eventSubscriber = this.eventManager.subscribe(
            'saleAmt1ListModification',
            (response) => this.load(this.saleAmt1.id)
        );
    }
}
