import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    MenuDataService,
    MenuDataPopupService,
    MenuDataComponent,
    MenuDataDetailComponent,
    MenuDataDialogComponent,
    MenuDataPopupComponent,
    MenuDataDeletePopupComponent,
    MenuDataDeleteDialogComponent,
    menuDataRoute,
    menuDataPopupRoute,
    MenuDataResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...menuDataRoute,
    ...menuDataPopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MenuDataComponent,
        MenuDataDetailComponent,
        MenuDataDialogComponent,
        MenuDataDeleteDialogComponent,
        MenuDataPopupComponent,
        MenuDataDeletePopupComponent,
    ],
    entryComponents: [
        MenuDataComponent,
        MenuDataDialogComponent,
        MenuDataPopupComponent,
        MenuDataDeleteDialogComponent,
        MenuDataDeletePopupComponent,
    ],
    providers: [
        MenuDataService,
        MenuDataPopupService,
        MenuDataResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwMenuDataModule {}
