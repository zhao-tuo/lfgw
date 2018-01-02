import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectTransactionRecord } from './project-transaction-record.model';
import { ProjectTransactionRecordPopupService } from './project-transaction-record-popup.service';
import { ProjectTransactionRecordService } from './project-transaction-record.service';

@Component({
    selector: 'jhi-project-transaction-record-dialog',
    templateUrl: './project-transaction-record-dialog.component.html'
})
export class ProjectTransactionRecordDialogComponent implements OnInit {

    projectTransactionRecord: ProjectTransactionRecord;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private projectTransactionRecordService: ProjectTransactionRecordService,
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
        if (this.projectTransactionRecord.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projectTransactionRecordService.update(this.projectTransactionRecord));
        } else {
            this.subscribeToSaveResponse(
                this.projectTransactionRecordService.create(this.projectTransactionRecord));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProjectTransactionRecord>) {
        result.subscribe((res: ProjectTransactionRecord) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProjectTransactionRecord) {
        this.eventManager.broadcast({ name: 'projectTransactionRecordListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-project-transaction-record-popup',
    template: ''
})
export class ProjectTransactionRecordPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectTransactionRecordPopupService: ProjectTransactionRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectTransactionRecordPopupService
                    .open(ProjectTransactionRecordDialogComponent as Component, params['id']);
            } else {
                this.projectTransactionRecordPopupService
                    .open(ProjectTransactionRecordDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
