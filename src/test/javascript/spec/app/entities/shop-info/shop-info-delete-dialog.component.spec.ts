/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { ShopInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/shop-info/shop-info-delete-dialog.component';
import { ShopInfoService } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.service';

describe('Component Tests', () => {

    describe('ShopInfo Management Delete Component', () => {
        let comp: ShopInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ShopInfoDeleteDialogComponent>;
        let service: ShopInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ShopInfoDeleteDialogComponent],
                providers: [
                    ShopInfoService
                ]
            })
            .overrideTemplate(ShopInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShopInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopInfoService);
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
