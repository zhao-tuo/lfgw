/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { MenuDataComponent } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.component';
import { MenuDataService } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.service';
import { MenuData } from '../../../../../../main/webapp/app/entities/menu-data/menu-data.model';

describe('Component Tests', () => {

    describe('MenuData Management Component', () => {
        let comp: MenuDataComponent;
        let fixture: ComponentFixture<MenuDataComponent>;
        let service: MenuDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [MenuDataComponent],
                providers: [
                    MenuDataService
                ]
            })
            .overrideTemplate(MenuDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MenuDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MenuData(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.menuData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
