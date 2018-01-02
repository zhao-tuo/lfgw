import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectInfo } from './project-info.model';
import { ProjectInfoPopupService } from './project-info-popup.service';
import { ProjectInfoService } from './project-info.service';

@Component({
    selector: 'jhi-project-info-dialog',
    templateUrl: './project-info-dialog.component.html'
})
export class ProjectInfoDialogComponent implements OnInit {

    projectInfo: ProjectInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private projectInfoService: ProjectInfoService,
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
        if (this.projectInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projectInfoService.update(this.projectInfo));
        } else {
            this.subscribeToSaveResponse(
                this.projectInfoService.create(this.projectInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProjectInfo>) {
        result.subscribe((res: ProjectInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProjectInfo) {
        this.eventManager.broadcast({ name: 'projectInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-project-info-popup',
    template: ''
})
export class ProjectInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectInfoPopupService: ProjectInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectInfoPopupService
                    .open(ProjectInfoDialogComponent as Component, params['id']);
            } else {
                this.projectInfoPopupService
                    .open(ProjectInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
