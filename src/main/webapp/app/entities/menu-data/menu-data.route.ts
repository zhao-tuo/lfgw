import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MenuDataComponent } from './menu-data.component';
import { MenuDataDetailComponent } from './menu-data-detail.component';
import { MenuDataPopupComponent } from './menu-data-dialog.component';
import { MenuDataDeletePopupComponent } from './menu-data-delete-dialog.component';

@Injectable()
export class MenuDataResolvePagingParams implements Resolve<any> {

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

export const menuDataRoute: Routes = [
    {
        path: 'menu-data',
        component: MenuDataComponent,
        resolve: {
            'pagingParams': MenuDataResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.menuData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'menu-data/:id',
        component: MenuDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.menuData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const menuDataPopupRoute: Routes = [
    {
        path: 'menu-data-new',
        component: MenuDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.menuData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menu-data/:id/edit',
        component: MenuDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.menuData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menu-data/:id/delete',
        component: MenuDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.menuData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
