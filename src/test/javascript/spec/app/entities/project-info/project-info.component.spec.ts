/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { ProjectInfoComponent } from '../../../../../../main/webapp/app/entities/project-info/project-info.component';
import { ProjectInfoService } from '../../../../../../main/webapp/app/entities/project-info/project-info.service';
import { ProjectInfo } from '../../../../../../main/webapp/app/entities/project-info/project-info.model';

describe('Component Tests', () => {

    describe('ProjectInfo Management Component', () => {
        let comp: ProjectInfoComponent;
        let fixture: ComponentFixture<ProjectInfoComponent>;
        let service: ProjectInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectInfoComponent],
                providers: [
                    ProjectInfoService
                ]
            })
            .overrideTemplate(ProjectInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ProjectInfo(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.projectInfos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
