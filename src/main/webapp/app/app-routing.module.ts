import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { JhiMainComponent } from './layouts';
import {AppComponent} from "./app.component";

const ROOT_ROUTES:Routes = [
    {
        path:'',redirectTo:'/login',pathMatch:'full'
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(ROOT_ROUTES, { useHash: true })
    ],
    exports: [
        RouterModule
    ]
})
export class LfgwAppRoutingModule {}
