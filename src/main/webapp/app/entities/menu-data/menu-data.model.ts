import { BaseEntity } from './../../shared';

export class MenuData implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public keyWord?: string,
        public icon?: string,
        public url?: string,
        public expended?: boolean,
        public menuType?: number,
        public parentId?: number,
        public parentName?:string,
        public children?:MenuData[]
    ) {
        this.expended = false;
    }
}
