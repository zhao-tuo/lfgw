/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { ShopInfoDetailComponent } from '../../../../../../main/webapp/app/entities/shop-info/shop-info-detail.component';
import { ShopInfoService } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.service';
import { ShopInfo } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.model';

describe('Component Tests', () => {

    describe('ShopInfo Management Detail Component', () => {
        let comp: ShopInfoDetailComponent;
        let fixture: ComponentFixture<ShopInfoDetailComponent>;
        let service: ShopInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ShopInfoDetailComponent],
                providers: [
                    ShopInfoService
                ]
            })
            .overrideTemplate(ShopInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShopInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ShopInfo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shopInfo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
