import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    SaleAmt1Service,
    SaleAmt1PopupService,
    SaleAmt1Component,
    SaleAmt1DetailComponent,
    SaleAmt1DialogComponent,
    SaleAmt1PopupComponent,
    SaleAmt1DeletePopupComponent,
    SaleAmt1DeleteDialogComponent,
    saleAmt1Route,
    saleAmt1PopupRoute,
    SaleAmt1ResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...saleAmt1Route,
    ...saleAmt1PopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SaleAmt1Component,
        SaleAmt1DetailComponent,
        SaleAmt1DialogComponent,
        SaleAmt1DeleteDialogComponent,
        SaleAmt1PopupComponent,
        SaleAmt1DeletePopupComponent,
    ],
    entryComponents: [
        SaleAmt1Component,
        SaleAmt1DialogComponent,
        SaleAmt1PopupComponent,
        SaleAmt1DeleteDialogComponent,
        SaleAmt1DeletePopupComponent,
    ],
    providers: [
        SaleAmt1Service,
        SaleAmt1PopupService,
        SaleAmt1ResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwSaleAmt1Module {}
