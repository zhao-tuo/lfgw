import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LfgwProjectInfoModule } from './project-info/project-info.module';
import { LfgwProjectTransactionRecordModule } from './project-transaction-record/project-transaction-record.module';
import { LfgwShopInfoModule } from './shop-info/shop-info.module';
import { LfgwSaleAmt1Module } from './sale-amt-1/sale-amt-1.module';
import { LfgwSaleAmt2Module } from './sale-amt-2/sale-amt-2.module';
import {RouterModule} from "@angular/router";
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RouterModule.forChild([]),
        LfgwProjectInfoModule,
        LfgwProjectTransactionRecordModule,
        LfgwShopInfoModule,
        LfgwSaleAmt1Module,
        LfgwSaleAmt2Module
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwEntityModule {}
