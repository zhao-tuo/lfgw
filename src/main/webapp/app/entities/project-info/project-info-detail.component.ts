import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProjectInfo } from './project-info.model';
import { ProjectInfoService } from './project-info.service';

@Component({
    selector: 'jhi-project-info-detail',
    templateUrl: './project-info-detail.component.html'
})
export class ProjectInfoDetailComponent implements OnInit, OnDestroy {

    projectInfo: ProjectInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private projectInfoService: ProjectInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjectInfos();
    }

    load(id) {
        this.projectInfoService.find(id).subscribe((projectInfo) => {
            this.projectInfo = projectInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjectInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectInfoListModification',
            (response) => this.load(this.projectInfo.id)
        );
    }
}
