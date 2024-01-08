USE demo1;

-- Delete all records from the MetricsTable
DELETE FROM MetricsTable;

-- Delete all records from the DockerInstances
DELETE FROM DockerInstances;

-- Reset the identity value for MetricsTable to 0
DBCC CHECKIDENT ('MetricsTable', RESEED, 0);

-- Reset the identity value for DockerInstances to 0
DBCC CHECKIDENT ('DockerInstances', RESEED, 0);
