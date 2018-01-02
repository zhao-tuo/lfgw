import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    ProjectInfoService,
    ProjectInfoPopupService,
    ProjectInfoComponent,
    ProjectInfoDetailComponent,
    ProjectInfoDialogComponent,
    ProjectInfoPopupComponent,
    ProjectInfoDeletePopupComponent,
    ProjectInfoDeleteDialogComponent,
    projectInfoRoute,
    projectInfoPopupRoute,
    ProjectInfoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectInfoRoute,
    ...projectInfoPopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectInfoComponent,
        ProjectInfoDetailComponent,
        ProjectInfoDialogComponent,
        ProjectInfoDeleteDialogComponent,
        ProjectInfoPopupComponent,
        ProjectInfoDeletePopupComponent,
    ],
    entryComponents: [
        ProjectInfoComponent,
        ProjectInfoDialogComponent,
        ProjectInfoPopupComponent,
        ProjectInfoDeleteDialogComponent,
        ProjectInfoDeletePopupComponent,
    ],
    providers: [
        ProjectInfoService,
        ProjectInfoPopupService,
        ProjectInfoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwProjectInfoModule {}
