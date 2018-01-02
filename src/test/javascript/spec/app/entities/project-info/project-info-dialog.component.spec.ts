/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ProjectInfoDialogComponent } from '../../../../../../main/webapp/app/entities/project-info/project-info-dialog.component';
import { ProjectInfoService } from '../../../../../../main/webapp/app/entities/project-info/project-info.service';
import { ProjectInfo } from '../../../../../../main/webapp/app/entities/project-info/project-info.model';

describe('Component Tests', () => {

    describe('ProjectInfo Management Dialog Component', () => {
        let comp: ProjectInfoDialogComponent;
        let fixture: ComponentFixture<ProjectInfoDialogComponent>;
        let service: ProjectInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectInfoDialogComponent],
                providers: [
                    ProjectInfoService
                ]
            })
            .overrideTemplate(ProjectInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProjectInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.projectInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'projectInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProjectInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.projectInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'projectInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
