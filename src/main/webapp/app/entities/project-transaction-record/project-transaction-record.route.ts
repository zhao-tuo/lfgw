import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProjectTransactionRecordComponent } from './project-transaction-record.component';
import { ProjectTransactionRecordDetailComponent } from './project-transaction-record-detail.component';
import { ProjectTransactionRecordPopupComponent } from './project-transaction-record-dialog.component';
import { ProjectTransactionRecordDeletePopupComponent } from './project-transaction-record-delete-dialog.component';

@Injectable()
export class ProjectTransactionRecordResolvePagingParams implements Resolve<any> {

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

export const projectTransactionRecordRoute: Routes = [
    {
        path: 'project-transaction-record',
        component: ProjectTransactionRecordComponent,
        resolve: {
            'pagingParams': ProjectTransactionRecordResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectTransactionRecord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'project-transaction-record/:id',
        component: ProjectTransactionRecordDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectTransactionRecord.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectTransactionRecordPopupRoute: Routes = [
    {
        path: 'project-transaction-record-new',
        component: ProjectTransactionRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectTransactionRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'project-transaction-record/:id/edit',
        component: ProjectTransactionRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectTransactionRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'project-transaction-record/:id/delete',
        component: ProjectTransactionRecordDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.projectTransactionRecord.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
