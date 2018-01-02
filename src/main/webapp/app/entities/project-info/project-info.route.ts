import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProjectInfoComponent } from './project-info.component';
import { ProjectInfoDetailComponent } from './project-info-detail.component';
import { ProjectInfoPopupComponent } from './project-info-dialog.component';
import { ProjectInfoDeletePopupComponent } from './project-info-delete-dialog.component';

@Injectable()
export class ProjectInfoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const projectInfoRoute: Routes = [
    {
        path: 'project-info',
        component: ProjectInfoComponent,
        resolve: {
            'pagingParams': ProjectInfoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'project-info/:id',
        component: ProjectInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectInfoPopupRoute: Routes = [
    {
        path: 'project-info-new',
        component: ProjectInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'project-info/:id/edit',
        component: ProjectInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'project-info/:id/delete',
        component: ProjectInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
