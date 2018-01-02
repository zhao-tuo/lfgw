/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { ShopInfoComponent } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.component';
import { ShopInfoService } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.service';
import { ShopInfo } from '../../../../../../main/webapp/app/entities/shop-info/shop-info.model';

describe('Component Tests', () => {

    describe('ShopInfo Management Component', () => {
        let comp: ShopInfoComponent;
        let fixture: ComponentFixture<ShopInfoComponent>;
        let service: ShopInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ShopInfoComponent],
                providers: [
                    ShopInfoService
                ]
            })
            .overrideTemplate(ShopInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShopInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ShopInfo(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shopInfos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
