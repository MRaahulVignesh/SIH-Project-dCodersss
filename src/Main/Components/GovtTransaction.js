import React, { Component } from 'react';
import './GovtTransaction.css'
import Ticker from 'react-ticker'
import { Table, Input, Button, Space , Modal} from 'antd';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';

const data = [
    {
      key: '1',
      sell: 'Ram Kumar',
      loc:"Gorakhpur, UP",
      date:'1 August 2020',
      buy:"Ark Srivastava",
      quantity:"1000",
      paym:'PAYTM', 
      amt:312.44
    },
    {
      key: '2',
      date:'30 July 2020', 
      sell:'Kamlesh Khanna',
      buy:"Ark Srivastava",
      quantity:"1000", 
      loc:'Chennai, TN', 
      paym:'PAYTM', 
      amt:866.99
    },{
      key: '3',
      date:'29 July 2020', 
      sell:'Pradeep Kumar', 
      buy:"Ark Srivastava",
      quantity:"1000",
      loc:'Trichy, TN', 
      paym:'ONLINE SBI', 
      amt:100.81
    },{
      key: '4',
      date:'26 July 2020', 
      sell:'Nikhil Sebastian',
      buy:"Ark Srivastava",
      quantity:"1000",
      loc:'Cochin, KL', 
      paym:'GPAY', 
      amt:654.39
    },{
      key: '5',
      date:'19 July 2020', 
      sell:'Maha Fatheema',
      buy:"Ark Srivastava",
      quantity:"1000",
      loc:'Mumbai, MH', 
      paym:'ONLINE SBI', 
      amt:212.79
    },
];

class GovtTransaction extends Component{

    state = {
        modalVisible: false,
        searchText: '',
        searchedColumn: '',
    }
    
    
    getColumnSearchProps = dataIndex => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
        <div style={{ padding: 8 }}>
        <Input
            ref={node => {
            this.searchInput = node;
            }}
            placeholder={`Search ${dataIndex}`}
            value={selectedKeys[0]}
            onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
            style={{ width: 188, marginBottom: 8, display: 'block' }}
        />
        <Space>
            <Button
            type="primary"
            onClick={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
            icon={<SearchOutlined />}
            size="small"
            style={{ width: 90 }}
            >
            Search
            </Button>
            <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
            Reset
            </Button>
        </Space>
        </div>
    ),
    filterIcon: filtered => <SearchOutlined style={{ color: filtered ? '#1890ff' : undefined }} />,
    onFilter: (value, record) =>
        record[dataIndex] ? record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()) : '',
    onFilterDropdownVisibleChange: visible => {
        if (visible) {
        setTimeout(() => this.searchInput.select());
        }
    },
    render: text =>
        this.state.searchedColumn === dataIndex ? (
        <Highlighter
            highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
            searchWords={[this.state.searchText]}
            autoEscape
            textToHighlight={text ? text.toString() : ''}
        />
        ) : (
        text
        ),
    });

    handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        this.setState({
            searchText: selectedKeys[0],
            searchedColumn: dataIndex,
        });
    };

    handleReset = clearFilters => {
        clearFilters();
        this.setState({ searchText: '' });
    };
    showDialog = (e) => {
        this.setState({modalVisible:true})
    }
    setModalVisible(modalVisible) {
        this.setState({ modalVisible });
    }
    render(){
        const columns = [
            {
              title: 'Date',
              dataIndex: 'date',
              key: 'date',
              width: '30%',
              ...this.getColumnSearchProps('date'),
            },
            {
              title: 'Seller',
              dataIndex: 'sell',
              key: 'seller',
              width: '20%',
              ...this.getColumnSearchProps('seller'),
            },
            {
              title: 'Location',
              dataIndex: 'loc',
              key: 'loc',
              width: '20%',
              ...this.getColumnSearchProps('loc'),
            },
            {
              title: 'Buyer',
              dataIndex: 'buy',
              key: 'buy',
              width: '20%',
              ...this.getColumnSearchProps('buy'),
            },
            {
              title: 'Quantity',
              dataIndex: 'quantity',
              key: 'quantity',
              width: '20%',
              ...this.getColumnSearchProps('quantity'),
            },
            {
              title: 'Payment Method',
              dataIndex: 'paym',
              key: 'paym',
              width: '40%',
              ...this.getColumnSearchProps('paym'),
            },
            {
              title: 'Amount',
              dataIndex: 'amt',
              key: 'amt',
              width: '20%',
              ...this.getColumnSearchProps('amt'),
            },
        ];
        return(
            <div id="main">
                <Ticker direction="toRight" height="30">
                    {({ index }) => (
                        <div style={{fontFamily:"'Times New Roman', Times, serif"}}>
                            <h2>This is the Headline of element #{index}!</h2>
                            <img src="www.my-image-source.com/" alt=""/>
                        </div>
                    )}
                </Ticker>
                <div id="transaction">
                    <div id="transactions">
                        <Table     pagination={{ defaultPageSize: 5, showSizeChanger: true, pageSizeOptions: ['5', '10', '20']}} columns={columns} dataSource={data} />
                    </div>
                </div>
            </div>
        )
    }
}

export default GovtTransaction;