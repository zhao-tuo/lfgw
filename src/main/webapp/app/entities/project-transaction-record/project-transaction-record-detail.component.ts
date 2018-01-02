import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectTransactionRecord } from './project-transaction-record.model';
import { ProjectTransactionRecordService } from './project-transaction-record.service';

@Component({
    selector: 'jhi-project-transaction-record-detail',
    templateUrl: './project-transaction-record-detail.component.html'
})
export class ProjectTransactionRecordDetailComponent implements OnInit, OnDestroy {

    projectTransactionRecord: ProjectTransactionRecord;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private projectTransactionRecordService: ProjectTransactionRecordService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjectTransactionRecords();
    }

    load(id) {
        this.projectTransactionRecordService.find(id).subscribe((projectTransactionRecord) => {
            this.projectTransactionRecord = projectTransactionRecord;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjectTransactionRecords() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectTransactionRecordListModification',
            (response) => this.load(this.projectTransactionRecord.id)
        );
    }
}
