/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ProjectInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/project-info/project-info-delete-dialog.component';
import { ProjectInfoService } from '../../../../../../main/webapp/app/entities/project-info/project-info.service';

describe('Component Tests', () => {

    describe('ProjectInfo Management Delete Component', () => {
        let comp: ProjectInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ProjectInfoDeleteDialogComponent>;
        let service: ProjectInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectInfoDeleteDialogComponent],
                providers: [
                    ProjectInfoService
                ]
            })
            .overrideTemplate(ProjectInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectInfoService);
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
