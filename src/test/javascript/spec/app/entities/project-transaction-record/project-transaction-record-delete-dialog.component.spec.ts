/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ProjectTransactionRecordDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record-delete-dialog.component';
import { ProjectTransactionRecordService } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.service';

describe('Component Tests', () => {

    describe('ProjectTransactionRecord Management Delete Component', () => {
        let comp: ProjectTransactionRecordDeleteDialogComponent;
        let fixture: ComponentFixture<ProjectTransactionRecordDeleteDialogComponent>;
        let service: ProjectTransactionRecordService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectTransactionRecordDeleteDialogComponent],
                providers: [
                    ProjectTransactionRecordService
                ]
            })
            .overrideTemplate(ProjectTransactionRecordDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectTransactionRecordDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectTransactionRecordService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
