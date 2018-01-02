import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SaleAmt1Component } from './sale-amt-1.component';
import { SaleAmt1DetailComponent } from './sale-amt-1-detail.component';
import { SaleAmt1PopupComponent } from './sale-amt-1-dialog.component';
import { SaleAmt1DeletePopupComponent } from './sale-amt-1-delete-dialog.component';

@Injectable()
export class SaleAmt1ResolvePagingParams implements Resolve<any> {

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

export const saleAmt1Route: Routes = [
    {
        path: 'sale-amt-1',
        component: SaleAmt1Component,
        resolve: {
            'pagingParams': SaleAmt1ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt1.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sale-amt-1/:id',
        component: SaleAmt1DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt1.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleAmt1PopupRoute: Routes = [
    {
        path: 'sale-amt-1-new',
        component: SaleAmt1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-amt-1/:id/edit',
        component: SaleAmt1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-amt-1/:id/delete',
        component: SaleAmt1DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
