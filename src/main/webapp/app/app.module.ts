import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { LfgwSharedModule, UserRouteAccessService } from './shared';
import { LfgwAppRoutingModule} from './app-routing.module';
import { LfgwHomeModule } from './home/home.module';
import { LfgwAdminModule } from './admin/admin.module';
import { LfgwAccountModule } from './account/account.module';
import { LfgwEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import {TreeComponent} from "./layouts/tree/tree.component";
import {SidebarMenuComponent} from "./layouts/tree/sidebar-menu.component";
import {TreeviewMenuComponent} from "./layouts/tree/treeview-menu.component";

@NgModule({
    imports: [
        BrowserModule,
        LfgwAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        LfgwSharedModule,
        LfgwHomeModule,
        LfgwAdminModule,
        LfgwAccountModule,
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
    ],
    bootstrap: [ JhiMainComponent ]
})
export class LfgwAppModule {}
