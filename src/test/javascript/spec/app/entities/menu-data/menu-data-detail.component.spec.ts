/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { MenuDataDetailComponent } from '../../../../../../main/webapp/app/entities/menu-data/menu-data-detail.component';
import { MenuDataService } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.service';
import { MenuData } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.model';

describe('Component Tests', () => {

    describe('MenuData Management Detail Component', () => {
        let comp: MenuDataDetailComponent;
        let fixture: ComponentFixture<MenuDataDetailComponent>;
        let service: MenuDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [MenuDataDetailComponent],
                providers: [
                    MenuDataService
                ]
            })
            .overrideTemplate(MenuDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MenuDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MenuData(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.menuData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
