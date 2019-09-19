import { Moment } from 'moment';

export interface ISnowflakeConnection {
  id?: number;
  name?: string;
  description?: string;
  url?: string;
  username?: string;
  password?: string;
  acct?: string;
  warehouse?: string;
  database?: string;
  schema?: string;
  valid?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
}

export class SnowflakeConnection implements ISnowflakeConnection {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public url?: string,
    public username?: string,
    public password?: string,
    public acct?: string,
    public warehouse?: string,
    public database?: string,
    public schema?: string,
    public valid?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment
  ) {
    // this.valid = this.valid || false;
  }
}
