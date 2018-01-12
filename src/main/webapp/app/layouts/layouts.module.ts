
import { NgModule } from '@angular/core';


import { LfgwSharedModule, UserRouteAccessService } from '../shared';
import { LayoutsRoutingModule } from './layouts-routing.module';
import { LfgwAdminModule } from '../admin/admin.module';
import { LfgwAccountModule } from '../account/account.module';
import { LfgwEntityModule } from '../entities/entity.module';
import { customHttpProvider } from '../blocks/interceptor/http.provider';
import { PaginationConfig } from '../blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent,
    TreeComponent,
    SidebarMenuComponent,
    TreeviewMenuComponent
} from './';
import {LfgwHomeModule} from "../home/home.module";


@NgModule({
    imports: [
        LfgwSharedModule,
        LayoutsRoutingModule,
        LfgwAdminModule,
        LfgwAccountModule,
        LfgwHomeModule,
        LfgwEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        TreeComponent,
        SidebarMenuComponent,
        TreeviewMenuComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ]
})
export class LayoutsModule {}
