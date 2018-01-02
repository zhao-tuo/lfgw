import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectTransactionRecord } from './project-transaction-record.model';
import { ProjectTransactionRecordPopupService } from './project-transaction-record-popup.service';
import { ProjectTransactionRecordService } from './project-transaction-record.service';

@Component({
    selector: 'jhi-project-transaction-record-delete-dialog',
    templateUrl: './project-transaction-record-delete-dialog.component.html'
})
export class ProjectTransactionRecordDeleteDialogComponent {

    projectTransactionRecord: ProjectTransactionRecord;

    constructor(
        private projectTransactionRecordService: ProjectTransactionRecordService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectTransactionRecordService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projectTransactionRecordListModification',
                content: 'Deleted an projectTransactionRecord'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-transaction-record-delete-popup',
    template: ''
})
export class ProjectTransactionRecordDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectTransactionRecordPopupService: ProjectTransactionRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.projectTransactionRecordPopupService
                .open(ProjectTransactionRecordDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
