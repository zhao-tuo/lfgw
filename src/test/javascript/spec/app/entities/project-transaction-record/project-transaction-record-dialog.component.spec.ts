/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ProjectTransactionRecordDialogComponent } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record-dialog.component';
import { ProjectTransactionRecordService } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.service';
import { ProjectTransactionRecord } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.model';

describe('Component Tests', () => {

    describe('ProjectTransactionRecord Management Dialog Component', () => {
        let comp: ProjectTransactionRecordDialogComponent;
        let fixture: ComponentFixture<ProjectTransactionRecordDialogComponent>;
        let service: ProjectTransactionRecordService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectTransactionRecordDialogComponent],
                providers: [
                    ProjectTransactionRecordService
                ]
            })
            .overrideTemplate(ProjectTransactionRecordDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectTransactionRecordDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectTransactionRecordService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProjectTransactionRecord(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.projectTransactionRecord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'projectTransactionRecordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProjectTransactionRecord();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.projectTransactionRecord = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'projectTransactionRecordListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
