import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    ShopInfoService,
    ShopInfoPopupService,
    ShopInfoComponent,
    ShopInfoDetailComponent,
    ShopInfoDialogComponent,
    ShopInfoPopupComponent,
    ShopInfoDeletePopupComponent,
    ShopInfoDeleteDialogComponent,
    shopInfoRoute,
    shopInfoPopupRoute,
    ShopInfoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shopInfoRoute,
    ...shopInfoPopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ShopInfoComponent,
        ShopInfoDetailComponent,
        ShopInfoDialogComponent,
        ShopInfoDeleteDialogComponent,
        ShopInfoPopupComponent,
        ShopInfoDeletePopupComponent,
    ],
    entryComponents: [
        ShopInfoComponent,
        ShopInfoDialogComponent,
        ShopInfoPopupComponent,
        ShopInfoDeleteDialogComponent,
        ShopInfoDeletePopupComponent,
    ],
    providers: [
        ShopInfoService,
        ShopInfoPopupService,
        ShopInfoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwShopInfoModule {}
