/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ShopInfoDialogComponent } from '../../../../../../main/webapp/app/entities/shop-info/shop-info-dialog.component';
import { ShopInfoService } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.service';
import { ShopInfo } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.model';
import { ProjectInfoService } from '../../../../../../main/webapp/app/entities/project-info';

describe('Component Tests', () => {

    describe('ShopInfo Management Dialog Component', () => {
        let comp: ShopInfoDialogComponent;
        let fixture: ComponentFixture<ShopInfoDialogComponent>;
        let service: ShopInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ShopInfoDialogComponent],
                providers: [
                    ProjectInfoService,
                    ShopInfoService
                ]
            })
            .overrideTemplate(ShopInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShopInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShopInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.shopInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shopInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ShopInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.shopInfo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'shopInfoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
