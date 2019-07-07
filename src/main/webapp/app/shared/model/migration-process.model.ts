import { Moment } from 'moment';

export interface IMigrationProcess {
  id?: number;
  name?: string;
  description?: string;
  type?: string;
  tablesToMigrate?: string;
  lastStatus?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  sourceConnectionName?: string;
  sourceConnectionId?: number;
  snowflakeConnectionName?: string;
  snowflakeConnectionId?: number;
}

export class MigrationProcess implements IMigrationProcess {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public type?: string,
    public tablesToMigrate?: string,
    public lastStatus?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public sourceConnectionName?: string,
    public sourceConnectionId?: number,
    public snowflakeConnectionName?: string,
    public snowflakeConnectionId?: number
  ) {}
}
