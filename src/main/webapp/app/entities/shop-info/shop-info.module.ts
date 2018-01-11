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
import {ShopInfoSaveComponent} from "./shop-info-save.component";

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
        ShopInfoSaveComponent,
    ],
    entryComponents: [
        ShopInfoComponent,
        ShopInfoDialogComponent,
        ShopInfoPopupComponent,
        ShopInfoDeleteDialogComponent,
        ShopInfoDeletePopupComponent,
        ShopInfoSaveComponent,
    ],
    providers: [
        ShopInfoService,
        ShopInfoPopupService,
        ShopInfoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwShopInfoModule {}
