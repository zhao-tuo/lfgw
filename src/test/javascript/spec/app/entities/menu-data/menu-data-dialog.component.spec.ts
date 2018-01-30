/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { MenuDataDialogComponent } from '../../../../../../main/webapp/app/entities/menu-data/menu-data-dialog.component';
import { MenuDataService } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.service';
import { MenuData } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.model';

describe('Component Tests', () => {

    describe('MenuData Management Dialog Component', () => {
        let comp: MenuDataDialogComponent;
        let fixture: ComponentFixture<MenuDataDialogComponent>;
        let service: MenuDataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [MenuDataDialogComponent],
                providers: [
                    MenuDataService
                ]
            })
            .overrideTemplate(MenuDataDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MenuDataDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuDataService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MenuData(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.menuData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'menuDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MenuData();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.menuData = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'menuDataListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
