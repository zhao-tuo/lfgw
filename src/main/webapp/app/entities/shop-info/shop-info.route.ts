import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ShopInfoComponent } from './shop-info.component';
import { ShopInfoDetailComponent } from './shop-info-detail.component';
import { ShopInfoPopupComponent } from './shop-info-dialog.component';
import { ShopInfoDeletePopupComponent } from './shop-info-delete-dialog.component';

@Injectable()
export class ShopInfoResolvePagingParams implements Resolve<any> {

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

export const shopInfoRoute: Routes = [
    {
        path: 'shop-info',
        component: ShopInfoComponent,
        resolve: {
            'pagingParams': ShopInfoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.shopInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shop-info/:id',
        component: ShopInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.shopInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shopInfoPopupRoute: Routes = [
    {
        path: 'shop-info-new',
        component: ShopInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.shopInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shop-info/:id/edit',
        component: ShopInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.shopInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shop-info/:id/delete',
        component: ShopInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.shopInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
