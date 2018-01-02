import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    SaleAmt2Service,
    SaleAmt2PopupService,
    SaleAmt2Component,
    SaleAmt2DetailComponent,
    SaleAmt2DialogComponent,
    SaleAmt2PopupComponent,
    SaleAmt2DeletePopupComponent,
    SaleAmt2DeleteDialogComponent,
    saleAmt2Route,
    saleAmt2PopupRoute,
    SaleAmt2ResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...saleAmt2Route,
    ...saleAmt2PopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SaleAmt2Component,
        SaleAmt2DetailComponent,
        SaleAmt2DialogComponent,
        SaleAmt2DeleteDialogComponent,
        SaleAmt2PopupComponent,
        SaleAmt2DeletePopupComponent,
    ],
    entryComponents: [
        SaleAmt2Component,
        SaleAmt2DialogComponent,
        SaleAmt2PopupComponent,
        SaleAmt2DeleteDialogComponent,
        SaleAmt2DeletePopupComponent,
    ],
    providers: [
        SaleAmt2Service,
        SaleAmt2PopupService,
        SaleAmt2ResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwSaleAmt2Module {}
