import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SaleAmt2 } from './sale-amt-2.model';
import { SaleAmt2Service } from './sale-amt-2.service';

@Component({
    selector: 'jhi-sale-amt-2-detail',
    templateUrl: './sale-amt-2-detail.component.html'
})
export class SaleAmt2DetailComponent implements OnInit, OnDestroy {

    saleAmt2: SaleAmt2;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private saleAmt2Service: SaleAmt2Service,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSaleAmt2S();
    }

    load(id) {
        this.saleAmt2Service.find(id).subscribe((saleAmt2) => {
            this.saleAmt2 = saleAmt2;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSaleAmt2S() {
        this.eventSubscriber = this.eventManager.subscribe(
            'saleAmt2ListModification',
            (response) => this.load(this.saleAmt2.id)
        );
    }
}
