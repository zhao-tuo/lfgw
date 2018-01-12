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
import { AppComponent } from './app.component';
import {LoginModule} from "./login/login.module";
import {LayoutsModule} from "./layouts/layouts.module";

@NgModule({
    imports: [
        BrowserModule,
        LfgwAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        LfgwSharedModule,
        LoginModule,
        LayoutsModule
    ],
    declarations: [
        AppComponent,
    ],
    providers: [
    ],
    bootstrap: [ AppComponent ]
})
export class LfgwAppModule {}
