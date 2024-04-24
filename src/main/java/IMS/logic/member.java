package IMS.logic;

import IMS.DB.DBC;
import IMS.data.membersData;

import java.sql.SQLException;

// Interface for the Builder
interface MemberBuilder {
    MemberBuilder setName(String name);

    MemberBuilder setAddress(String address);

    MemberBuilder setContact(String contact);

    MemberBuilder setMemberId(int memberId);

    member build() throws SQLException;
}

// Concrete Builder implementing the MemberBuilder interface
class DefaultMemberBuilder implements MemberBuilder {
    private String name;
    private String address;
    private String contact;
    private int memberId;

    @Override
    public MemberBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public MemberBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public MemberBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    @Override
    public MemberBuilder setMemberId(int memberId) {
        this.memberId = memberId;
        return this;
    }

    @Override
    public member build() throws SQLException {
        return new member(name, address, contact, memberId);
    }
}

public class member extends membersData {
    public member() throws SQLException {
        super();
    }

    // Make the constructor public to be accessible by the builder
    public member(String name, String address, String contact, int memberId) throws SQLException {
        super();
        createMember(name, address, contact, memberId);
    }

    public void createMember(String name, String address, String contact, int memberId) {
        DBC dbc = new DBC();
        dbc.insertMemberData(memberId, name, address, contact);
    }

    // Method to create a builder instance
    public static MemberBuilder builder() {
        return new DefaultMemberBuilder();
    }
}
