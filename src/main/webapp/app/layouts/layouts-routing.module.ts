import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { errorRoute } from './';
import {JhiMainComponent} from "./main/main.component";

const LAYOUT_ROUTES:Routes = [
    ...errorRoute,
    {
        path:'app',
        component:JhiMainComponent,
        children:[
            { path: '', loadChildren: '../home/home.module#LfgwHomeModule' },
            { path: 'home', loadChildren: '../home/home.module#LfgwHomeModule' },
            { path: 'data', loadChildren: '../entities/shop-info/shop-info.module#LfgwShopInfoModule' },
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(LAYOUT_ROUTES)
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutsRoutingModule {}
