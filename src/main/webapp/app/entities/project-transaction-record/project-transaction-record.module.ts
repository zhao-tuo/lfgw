import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LfgwSharedModule } from '../../shared';
import {
    ProjectTransactionRecordService,
    ProjectTransactionRecordPopupService,
    ProjectTransactionRecordComponent,
    ProjectTransactionRecordDetailComponent,
    ProjectTransactionRecordDialogComponent,
    ProjectTransactionRecordPopupComponent,
    ProjectTransactionRecordDeletePopupComponent,
    ProjectTransactionRecordDeleteDialogComponent,
    projectTransactionRecordRoute,
    projectTransactionRecordPopupRoute,
    ProjectTransactionRecordResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectTransactionRecordRoute,
    ...projectTransactionRecordPopupRoute,
];

@NgModule({
    imports: [
        LfgwSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectTransactionRecordComponent,
        ProjectTransactionRecordDetailComponent,
        ProjectTransactionRecordDialogComponent,
        ProjectTransactionRecordDeleteDialogComponent,
        ProjectTransactionRecordPopupComponent,
        ProjectTransactionRecordDeletePopupComponent,
    ],
    entryComponents: [
        ProjectTransactionRecordComponent,
        ProjectTransactionRecordDialogComponent,
        ProjectTransactionRecordPopupComponent,
        ProjectTransactionRecordDeleteDialogComponent,
        ProjectTransactionRecordDeletePopupComponent,
    ],
    providers: [
        ProjectTransactionRecordService,
        ProjectTransactionRecordPopupService,
        ProjectTransactionRecordResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LfgwProjectTransactionRecordModule {}
