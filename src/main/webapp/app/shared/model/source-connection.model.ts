import { Moment } from 'moment';

export interface ISourceConnection {
  id?: number;
  name?: string;
  description?: string;
  sourceType?: string;
  url?: string;
  username?: string;
  password?: string;
  database?: string;
  host?: string;
  portnumber?: string;
  schema?: string;
  valid?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
}

export class SourceConnection implements ISourceConnection {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public sourceType?: string,
    public url?: string,
    public username?: string,
    public password?: string,
    public database?: string,
    public host?: string,
    public portnumber?: string,
    public schema?: string,
    public valid?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment
  ) {
    this.valid = this.valid || false;
  }
}
