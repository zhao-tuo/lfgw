import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectInfo } from './project-info.model';
import { ProjectInfoPopupService } from './project-info-popup.service';
import { ProjectInfoService } from './project-info.service';

@Component({
    selector: 'jhi-project-info-delete-dialog',
    templateUrl: './project-info-delete-dialog.component.html'
})
export class ProjectInfoDeleteDialogComponent {

    projectInfo: ProjectInfo;

    constructor(
        private projectInfoService: ProjectInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projectInfoListModification',
                content: 'Deleted an projectInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-info-delete-popup',
    template: ''
})
export class ProjectInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectInfoPopupService: ProjectInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.projectInfoPopupService
                .open(ProjectInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
