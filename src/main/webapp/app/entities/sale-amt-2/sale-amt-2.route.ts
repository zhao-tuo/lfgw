import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SaleAmt2Component } from './sale-amt-2.component';
import { SaleAmt2DetailComponent } from './sale-amt-2-detail.component';
import { SaleAmt2PopupComponent } from './sale-amt-2-dialog.component';
import { SaleAmt2DeletePopupComponent } from './sale-amt-2-delete-dialog.component';

@Injectable()
export class SaleAmt2ResolvePagingParams implements Resolve<any> {

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

export const saleAmt2Route: Routes = [
    {
        path: 'sale-amt-2',
        component: SaleAmt2Component,
        resolve: {
            'pagingParams': SaleAmt2ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt2.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sale-amt-2/:id',
        component: SaleAmt2DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt2.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleAmt2PopupRoute: Routes = [
    {
        path: 'sale-amt-2-new',
        component: SaleAmt2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-amt-2/:id/edit',
        component: SaleAmt2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sale-amt-2/:id/delete',
        component: SaleAmt2DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lfgwApp.saleAmt2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
